package com.ey.repository;

import com.ey.model.Profile;
import com.ey.model.Skill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserUserId(Long userId);

	List<Profile> findBySkillsContaining(Skill skill);
	void deleteByUser_UserId(Long userId);
	List<Profile> findAllBySkillsContaining(Skill skill);
}
