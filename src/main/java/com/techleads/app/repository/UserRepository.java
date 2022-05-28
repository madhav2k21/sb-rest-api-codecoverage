package com.techleads.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techleads.app.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByName(String name);

	@Query("select u from Users u where u.name =?1 and u.location =?2")
	Users findByJPQL(String name, String location);

	@Query("select u from Users u where u.name =:name and u.location =:location")
	Users findByJPQLNamedParams(@Param("name") String name, @Param("location") String location);

	@Query(value = "select u.id, u.name, u.location from users u where u.name=?1 and u.location=?2", nativeQuery = true)
	Users findByNativeQuery(String name, String location);
	
	@Query(value = "select u.id, u.name, u.location from users u where u.name =:name and u.location =:location", nativeQuery = true)
	Users findByNativeQueryNamedParam(@Param("name") String name, @Param("location") String location);
	
	List<Users> findAllByOrderByCreatedDateTimeDescUpdatedDateTimeDesc();
//	List<Person> findByLastnameOrderByFirstnameDesc(String lastname);

}
