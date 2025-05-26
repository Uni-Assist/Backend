package com.example.UniAssist.model.projection;

import java.util.UUID;

public interface FullNameProjection {
    UUID getId();
    String getLastName();
    String getFirstName();
    String getMiddleName();
}