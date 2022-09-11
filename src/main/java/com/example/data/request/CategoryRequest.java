package com.example.data.request;

import lombok.*;


/**
 * Created by Bad_sha
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private String categoryCode;
    private String categoryName;
    private String active;
}
