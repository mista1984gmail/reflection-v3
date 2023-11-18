package ru.clevertec.reflection.task.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.reflection.task.dao.postgresql.service.PostgreSQLCreateTables;

@Aspect
public class CreateTableAspect {

	@Pointcut("@annotation(ru.clevertec.reflection.task.aspect.annotation.CreateTable)")
	public void createTableMethod() {
	}

	@Before(value = "createTableMethod()")
	public void createTableProfiling(JoinPoint jp) {
		PostgreSQLCreateTables postgreSQLCreateTables = new PostgreSQLCreateTables();
		postgreSQLCreateTables.createTablesInDataBase();
	}

}
