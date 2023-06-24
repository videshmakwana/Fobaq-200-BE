package com.brilworks.accounts.repository;

import com.brilworks.accounts.dto.OrganisationDto;
import com.brilworks.accounts.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    List<Organisation> findAll();

    Optional<Organisation> findOrganisationByOrganisationUrl(String organizerURL);

    @Query("select DISTINCT new com.brilworks.accounts.dto.OrganisationDto(ur.organisation) from User u" +
            " inner join UserRole ur" +
            " on u.id=ur.userId" +
            " where u.id=:id")
    List<OrganisationDto> getListOfOrganisations(@Param("id") Long id);
}
