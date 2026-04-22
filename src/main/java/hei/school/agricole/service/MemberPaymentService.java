package hei.school.agricole.service;

import hei.school.agricole.dto.CreateMemberPayment;
import hei.school.agricole.entity.*;
import hei.school.agricole.enums.Frequency;
import hei.school.agricole.enums.PaymentMode;
import hei.school.agricole.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberPaymentService {
    private static final int REVERSE_PERCENTAGE = 0;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository feeRepository;
    private final MemberPaymentRepository paymentRepository;
    private final FinancialAccountRepository accountRepository;
    private final CollectivityTransactionRepository transactionRepository;

    public MemberPaymentService(MemberRepository memberRepository,
                                MembershipFeeRepository feeRepository,
                                MemberPaymentRepository paymentRepository,
                                FinancialAccountRepository accountRepository,
                                CollectivityTransactionRepository transactionRepository) {
        this.memberRepository = memberRepository;
        this.feeRepository = feeRepository;
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<MemberPayment> createPayments(String memberId, List<CreateMemberPayment> requests) {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new RuntimeException("Member not found");
        }

        return requests.stream().map(req -> {
            MembershipFee fee = feeRepository.findById(req.getMembershipFeeIdentifier());
            if (fee == null) {
                throw new RuntimeException("Fee not found");
            }

            FinancialAccount account = accountRepository.findById(req.getAccountCreditedIdentifier());
            if (account == null) {
                throw new RuntimeException("Financial account not found");
            }
            if (!account.getCollectivityId().equals(member.getCollectivityId())) {
                throw new RuntimeException("Account does not belong to member's collectivity");
            }

            if (req.getPaymentMode() == PaymentMode.CASH && !(account instanceof CashAccount)) {
                throw new RuntimeException("CASH payment requires a CashAccount");
            }
            if (req.getPaymentMode() == PaymentMode.MOBILE_BANKING && !(account instanceof MobileBankingAccount)) {
                throw new RuntimeException("MOBILE_BANKING payment requires a MobileBankingAccount");
            }
            if (req.getPaymentMode() == PaymentMode.BANK_TRANSFER && !(account instanceof BankAccount)) {
                throw new RuntimeException("BANK_TRANSFER requires a BankAccount");
            }

            MemberPayment payment = new MemberPayment();
            payment.setAmount(req.getAmount());
            payment.setPaymentMode(req.getPaymentMode());
            payment.setMembershipFeeId(fee.getId());
            payment.setCreationDate(LocalDate.now());

            MemberPayment savedPayment = paymentRepository.save(memberId, payment);

            CollectivityTransaction tx = new CollectivityTransaction();
            tx.setCollectivityId(member.getCollectivityId());
            tx.setCreationDate(LocalDate.now());
            tx.setAmount(Double.valueOf(req.getAmount()));
            tx.setPaymentMode(req.getPaymentMode());
            tx.setAccountCreditedId(account.getId());
            tx.setMemberDebitedId(memberId);
            transactionRepository.save(tx);

            accountRepository.increaseBalance(account.getId(), req.getAmount().doubleValue());

            if (fee.getFrequency() != Frequency.PUNCTUALLY && REVERSE_PERCENTAGE > 0) {
                double federationShare = req.getAmount() * REVERSE_PERCENTAGE / 100.0;
                FinancialAccount federationAccount = accountRepository.findFederationAccount();
                CollectivityTransaction fedTx = new CollectivityTransaction();
                fedTx.setCollectivityId(member.getCollectivityId());
                fedTx.setCreationDate(LocalDate.now());
                fedTx.setAmount(federationShare);
                fedTx.setPaymentMode(req.getPaymentMode());
                fedTx.setAccountCreditedId(federationAccount.getId());
                fedTx.setMemberDebitedId(memberId);
                transactionRepository.save(fedTx);
                accountRepository.increaseBalance(federationAccount.getId(), federationShare);
            }

            return savedPayment;
        }).collect(Collectors.toList());
    }
}