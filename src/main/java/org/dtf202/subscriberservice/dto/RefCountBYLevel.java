package org.dtf202.subscriberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefCountBYLevel {
    private Integer count;
    private Integer level;
}
