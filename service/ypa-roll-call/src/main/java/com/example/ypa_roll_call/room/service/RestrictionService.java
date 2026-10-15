package com.example.ypa_roll_call.room.service;

import com.example.ypa_roll_call.room.data.common.RestrictionDto;
import com.example.ypa_roll_call.room.data.request.GetRestrictionsDto;
import com.example.ypa_roll_call.room.data.response.RestrictionsDto;

public interface RestrictionService {
    void addRestriction(RestrictionDto dto);
    void removeRestriction(RestrictionDto dto);
    boolean hasRestriction(RestrictionDto dto);
    RestrictionsDto getRestrictions(GetRestrictionsDto dto);
}