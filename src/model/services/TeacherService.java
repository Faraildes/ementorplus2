package model.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.entities.Teacher;

public class TeacherService {

	public List<Teacher> findAll() {
		List<Teacher> list = new ArrayList<>();
		list.add(new Teacher(1, "Joa", "12345678987", "987654321", new Date(), 5000.00, "nao", "nao"));
		return list;
		}
}
