package ru.levelp.hospital.daoimpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.hospital.model.Department;

import java.util.List;

@Repository
@RepositoryRestResource(
        collectionResourceRel = "departments",
        itemResourceRel = "department"
)
public interface DepartmentDao extends JpaRepository<Department, Integer> {

    @Transactional
    default Department create(String departmentName) {
        Department department = new Department(departmentName);
        save(department);
        return department;
    }

    Department findByName(String name);

    @Query("from Department d where size(d.doctors) >= :min")
    List<Department> findBigDepartments(@Param("min") int minCount);
}
