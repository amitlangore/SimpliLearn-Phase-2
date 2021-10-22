package com.learner.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.learner.entity.Class;
import com.learner.entity.Student;
import com.learner.entity.Subject;
import com.learner.entity.Teacher;

public class Data {

	private DataSource dataSource;

	public Data(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() {

		List<Student> students = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;

		try {

			connection = dataSource.getConnection();

			String sql = "SELECT * FROM students";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);

			while (resultset.next()) {
				int id = resultset.getInt("id");
				String firstName = resultset.getString("fname");
				String lastName = resultset.getString("lname");
				int age = resultset.getInt("age");
				int aclass = resultset.getInt("class");

				Student tempStudent = new Student(id, firstName, lastName, age, aclass);

				students.add(tempStudent);

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement,resultset);
		}
		return students;

	}

	public List<Teacher> getTeachers() {

		List<Teacher> teachers = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;


		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM teachers";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);

			while (resultset.next()) {

				int id = resultset.getInt("id");
				String firstName = resultset.getString("fname");
				String lastName = resultset.getString("lname");
				
				Teacher temp = new Teacher(id, firstName, lastName);
				teachers.add(temp);

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement,resultset);
		}
		return teachers;

	}

	public List<Subject> getSubjects() {

		List<Subject> subjects = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;

		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM subjects";
			statement = connection.createStatement();

			resultset= statement.executeQuery(sql);
			while (resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				Subject temp = new Subject(id, name);
				subjects.add(temp);

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement,resultset);
		}
		return subjects;

	}

	public List<Class> getClasses() {

		List<Class> classes = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;


		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM classes";
			statement= connection.createStatement();
			resultset = statement.executeQuery(sql);

			while (resultset.next()) {
				int id = resultset.getInt("id");
				int section = resultset.getInt("section");
				int subject = resultset.getInt("subject");
				int teacher = resultset.getInt("teacher");
				String time = resultset.getString("time");

				Teacher tempTeacher = loadTeacher(teacher);
				Subject tempSubject = loadSubject(subject);

				String teacher_name = tempTeacher.getFname() + " " + tempTeacher.getLname();
				Class temp = new Class(id, section, teacher_name, tempSubject.getName(), time);
				classes.add(temp);

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement,resultset);
		}
		return classes;

	}

	public Teacher loadTeacher(int teacherId) {

		Teacher theTeacher = null;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;

		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM teachers WHERE id = " + teacherId;
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);
			
			while (resultset.next()) {
				int id = resultset.getInt("id");
				String fname = resultset.getString("fname");
				String lname = resultset.getString("lname");
				theTeacher = new Teacher(id, fname, lname);

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement,resultset);
		}
		return theTeacher;

	}

	public Subject loadSubject(int subjectId) {

		Subject theSubject = null;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;


		try {

			connection = dataSource.getConnection();
			String sql = "SELECT * FROM subjects WHERE id = " + subjectId;
			statement = connection.createStatement();

			resultset = statement.executeQuery(sql);
			while (resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getString("name");

				theSubject = new Subject(id, name);

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement,resultset);
		}
		return theSubject;

	}

	public Class loadClass(int classId) {

		Class theClass = null;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;


		try {

			connection = dataSource.getConnection();
			String sql = "SELECT * FROM clasess WHERE id = " + classId;
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);

			while (resultset.next()) {

				int id = resultset.getInt("id");
				int section = resultset.getInt("section");
				int subject = resultset.getInt("subject");
				int teacher = resultset.getInt("teacher");
				String time = resultset.getString("time");

				Teacher tempTeacher = loadTeacher(teacher);
				Subject tempSubject = loadSubject(subject);

				String teacher_name = tempTeacher.getFname() + " " + tempTeacher.getLname();
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// close JDBC objects
			close(connection, statement,resultset);
		}
		return theClass;

	}

	public List<Student> loadClassStudents(int classId) {

		List<Student> students = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;


		try {
			connection = dataSource.getConnection();

			String sql = "SELECT * FROM students WHERE class = " + classId;
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);

			while (resultset.next()) {
				int id = resultset.getInt("id");
				String firstName = resultset.getString("fname");
				String lastName = resultset.getString("lname");
				int age = resultset.getInt("age");
				int aclass =resultset.getInt("class");

				Student tempStudent = new Student(id, firstName, lastName, age, aclass);
				students.add(tempStudent);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement,resultset);
		}
		return students;

	}

	private void close(Connection connection, Statement statement, ResultSet resultset) {
		try {
			if (resultset != null) {
				resultset.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
