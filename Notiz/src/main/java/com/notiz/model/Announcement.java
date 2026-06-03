package com.notiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "announcements")
public class Announcement extends BaseDocument {

    private String title;
    private String content;
    private String authorId;
    private String targetAudience;

}
