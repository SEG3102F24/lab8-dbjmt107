package seg3x02.employeeGql.resolvers

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.UUID

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee>{
        return employeesRepository.findAll()
    }

    @QueryMapping
    fun getEmployees(@Argument employeeId: String): Employee? {
        val employee = employeesRepository.findById(employeeId)
        return employee.orElse(null)
    }

    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput): Employee{
        if(input.city!= null && input.name!=null && input.dateOfBirth!=null && input.salary!=null){
            val employee = Employee(input.name, input.dateOfBirth, input.city, input.salary,input.gender,input.email)
            employee.id = UUID.randomUUID().toString()
            employeesRepository.save(employee)
            return employee
        }else{
            throw Exception("invalid")
        }
    }
}
