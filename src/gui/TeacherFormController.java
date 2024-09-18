package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Teacher;
import model.services.TeacherService;

public class TeacherFormController implements Initializable{

	private Teacher entity;
	
	private TeacherService service;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtPhone;
	 
	@FXML
	private DatePicker dpAdmissionDate;
	
	@FXML
	private TextField txtSalary;
	
	@FXML
	private TextField txtChief;
	
	@FXML
	private TextField txtCoordinator;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorCpf;
	
	@FXML
	private Label labelErrorPhone;
	
	@FXML
	private Label labelErrorAdmissionDate;
	
	@FXML
	private Label labelErrorSalary;
	
	@FXML
	private Label labelErrorChief;
	
	@FXML
	private Label labelErrorCoordinator;
	
	@FXML
	private Button btSave;	

	@FXML
	private Button btCancel;
	
	public void setTeacher(Teacher entity) {
		this.entity = entity;
	}
	
	public void setTeacherService(TeacherService service) {
		this.service = service;
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null)
			throw new IllegalStateException("Entity was null!");
		if(service == null)
			throw new IllegalStateException("Service was null!");
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Teacher getFormData() {
		Teacher obj = new Teacher();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		obj.setCpf(txtCpf.getText());
		obj.setPhone(txtPhone.getText());
		Instant instant = Instant.from(dpAdmissionDate.getValue().atStartOfDay(ZoneId.systemDefault()));
		obj.setAdmissionDate(Date.from(instant));
		obj.setSalary(Utils.tryParseToDouble(txtSalary.getText()));
		obj.setChief(txtChief.getText());
		obj.setCoordinator(txtCoordinator.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldMaxLength(txtCpf, 12);
		Constraints.setTextFieldMaxLength(txtPhone, 15);
		Utils.formatDatePicker(dpAdmissionDate, "dd/MM/yyyy");
		Constraints.setTextFieldDouble(txtSalary);
		Constraints.setTextFieldMaxLength(txtChief, 3);
		Constraints.setTextFieldMaxLength(txtCoordinator, 3);		
	}
	
	public void updateFormData() {
		if(entity == null)
			throw new IllegalStateException("Entity was null");
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtCpf.setText(entity.getCpf());
		txtPhone.setText(entity.getPhone());
		if (entity.getAdmissionDate() != null) 
			dpAdmissionDate.setValue(LocalDate.ofInstant(entity.getAdmissionDate().toInstant(), ZoneId.systemDefault()));		
		Locale.setDefault(Locale.US);
		txtSalary.setText(String.format("%.2f", entity.getSalary()));
		txtChief.setText(entity.getChief());
		txtCoordinator.setText(entity.getCoordinator());		
	}
}	
