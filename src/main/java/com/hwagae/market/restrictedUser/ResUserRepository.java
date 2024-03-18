package com.hwagae.market.restrictedUser;


import com.hwagae.market.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ResUserRepository  extends JpaRepository<ResUserEntity,Integer> {

    ResUserEntity findByUserEntity(UserEntity userEntity);

    @Query("SELECT r.reportCount FROM ResUserEntity r WHERE "
            + "(CASE WHEN :selectValue = 'resUid' THEN r.resUid "
            + "      WHEN :selectValue = 'resUphone' THEN r.resUphone "
            + "      WHEN :selectValue = 'resAccount' THEN r.resAccount END) = :inputValue")
    Integer findReportCountBySelectValueAndInputValue(@Param("selectValue") String selectValue, @Param("inputValue") String inputValue);
}

