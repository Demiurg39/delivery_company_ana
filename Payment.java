import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    privat Integer id;

    @OneToOne
    @JoinColumn(name = "order_id, nullable = false")
    private Order order;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "PaymentStatusType")
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment-method", columnDefinition = "PaymentMetodType")
    privat PaymentMetod paymentMethod;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Payment(){}
    public Payment(Order order, Double amont, PaymentStatus status, PaymentMethod paymentMethod) {
        this.order = order;
        this.amount = amont;
        this.status = status;
        this.paymentMethod == paymentMethod;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}