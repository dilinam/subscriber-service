package org.dtf202.subscriberservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardMgt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String accountType;

    String cardType;

    String receivingAddress;

    @ManyToOne
    User user;

    String ChainName;
}
