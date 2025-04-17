package com.example.UniAssist.projection;

import java.util.UUID;

public interface FullNameProjection {
    UUID getId();
    String getLastName();
    String getFirstName();
    String getMiddleName();
}