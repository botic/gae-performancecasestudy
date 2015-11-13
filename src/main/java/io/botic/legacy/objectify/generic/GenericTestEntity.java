package io.botic.legacy.objectify.generic;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import io.botic.legacy.utils.BenchUtils;

@Entity
public class GenericTestEntity {
    @Id
    private Long id;

    @Index
    private String suffixedTitle;

    @Index
    private String title;

    public GenericTestEntity(String entityTitle) {
        this.title = entityTitle;

        // Create the two different
        final String randomString = BenchUtils.randomString(15);
        this.suffixedTitle = entityTitle + randomString;
    }

    public GenericTestEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuffixedTitle() {
        return suffixedTitle;
    }

    public void setSuffixedTitle(String suffixedTitle) {
        this.suffixedTitle = suffixedTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
