package goorm.coutfit.point.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import goorm.coutfit.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "charge_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @CreationTimestamp
    private LocalDateTime chargedAt;

    public static ChargeHistory create(User user, int amount, PaymentMethod paymentMethod) {
        return ChargeHistory.builder()
                .user(user)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .build();
    }
}
