package goorm.coutfit.store.domain;

import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String storeNumber;

    @Column(nullable = false)
    private Long categoryCode;

    @Column(nullable = false, length = 50)
    private String categoryName;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_category_id", nullable = false)
    private RegionCategory regionCategory;

    private LocalTime openTime;
    private LocalTime closeTime;

    @Column(nullable = false, length = 30)
    private String phoneNumber;

    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private StoreImage storeImage;

    public boolean isOpenNow() {
        if (openTime == null || closeTime == null) return false;

        LocalTime now = LocalTime.now();

        if (closeTime.isBefore(openTime)) {
            return now.isAfter(openTime) || now.isBefore(closeTime);
        } else {
            return !now.isBefore(openTime) && !now.isAfter(closeTime);

        }
    }
}
