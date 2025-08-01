package goorm.coutfit.point.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.UpdateTimestamp;

import goorm.coutfit.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer pointBalance;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public int getExpireInDays() {
        LocalDate now = LocalDate.now();
        LocalDate expireDate = expiredAt.toLocalDate();

        int days = (int) ChronoUnit.DAYS.between(now, expireDate);
        return days < 0 ? 0 : days;
    }

    public void charge(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 작을 수 없습니다.");
        }
        this.pointBalance += amount;
    }

    public static Point create(User user) {
        return Point.builder()
                .user(user)
                .pointBalance(0)
                .expiredAt(LocalDateTime.now().plusDays(30)) // TODO: 임시로 30일 후로 설정
                .build();
    }
}

