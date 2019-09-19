package com.medsec.util;

import com.medsec.dao.*;
import com.medsec.entity.*;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates all database queries.
 */
public class Database {
    SqlSession session = ConfigListener.sqlSessionFactory.openSession();
    boolean keepAlive = false;

    public Database() {
    }

    public Database(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
    /*
        User
     */
    public User getUserById(String id) {

        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.selectById(id);

        } finally {
            if (!keepAlive) close();
        }
    }

    public User getUserByEmail(String email) {

        try {

            UserMapper mapper=session.getMapper(UserMapper.class);
            return mapper.selectByEmail(email);

        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateTokenValidFromDate(String uid) {

        try {

            UserMapper mapper=session.getMapper(UserMapper.class);
            Instant now = Instant.now();
            User user = new User().id(uid).token_valid_from(now);
            mapper.updateToken(user);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateUserPassword(String uid, String password) {

        try {

            UserMapper mapper = session.getMapper(UserMapper.class);
            Instant now = Instant.now();
            User user  = new User().id(uid).password(password).token_valid_from(now);
            mapper.updatePassword(user);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    /*
        Appointment
     */
    public List<Appointment> listUserAppointments(
            String uid,
            @Nullable String since,
            @Nullable String until,
            @Nullable AppointmentStatus status){

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.getAppointmentsByUserId(uid, since, until, status);

        } finally {
            if (!keepAlive) close();
        }
    }

    public Appointment getAppointment(String appointment_id) {

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.getAppointmentById(appointment_id);

        } finally {
            if (!keepAlive) close();
        }
    }

    public Appointment updateUserNote(String appointment_id, String user_note) {

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            Appointment appointment = new Appointment()
                    .id(appointment_id)
                    .user_note(user_note);
            mapper.updateUserNoteById(appointment);
            session.commit();

            return mapper.getAppointmentById(appointment_id);

        } finally {
            if (!keepAlive) close();
        }
    }

    public void deleteUserNote(String appointment_id) {

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            Appointment appointment = new Appointment()
                    .id(appointment_id)
                    .user_note(null);
            mapper.updateUserNoteById(appointment);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateAppointmentStatus(String appointment_id, AppointmentStatus status) {

        try {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            Appointment appointment = new Appointment()
                    .id(appointment_id)
                    .status(status);
            mapper.updateStatusById(appointment);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    /*
    Hospital
     */
    public List<Hospital> selectAllHospitals(){

        try{
            HospitalMapper mapper=session.getMapper(HospitalMapper.class);
            return mapper.selectAllHospitals();

        } finally {
            if (!keepAlive) close();
        }
    }

    public Hospital selectOneHospital(String hospitalID){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        return mapper.selectOneHospital(hospitalID);
    }

    public void deleteHospital(String hospitalID){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        mapper.deleteHospital(hospitalID);
        session.commit();

    }

    public void updateHospital(Hospital hospital){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        mapper.updateHospital(hospital);
        session.commit();

    }

    public void addHospital(Hospital hospital){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        mapper.addHospital(hospital);
        session.commit();

    }

    /*
    Doctor
     */
    public List<Doctor> selectAllDoctors(){

        try{
            DoctorMapper mapper=session.getMapper(DoctorMapper.class);
            return mapper.selectAllDoctors();

        } finally {
            if(!keepAlive) close();
        }
    }

    public Doctor selectOneDoctor(String doctorID){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        return mapper.selectOneDoctor(doctorID);
    }

    public void deleteDoctor(String doctorID){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        mapper.deleteDoctor(doctorID);
        session.commit();
    }

    public void updateDoctor(Doctor doctor){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        mapper.updateDoctor(doctor);
        session.commit();
    }

    public void addDoctor(Doctor doctor){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        mapper.addDoctor(doctor);
        session.commit();
    }

    /*
    Pathology
     */
    public List<Pathology> selectAllPathologies(){
        try{
            PathologyMapper mapper=session.getMapper(PathologyMapper.class);
            return mapper.selectAllPathologies();

        } finally {
            if(!keepAlive) close();
        }
    }

    public Pathology selectOnePathology(String pathologyID){
        PathologyMapper mapper=session.getMapper(PathologyMapper.class);
        return mapper.selectOnePathology(pathologyID);
    }

    public void addPathology(Pathology pathology){
        PathologyMapper mapper=session.getMapper(PathologyMapper.class);
        mapper.addPathology(pathology);
        session.commit();
    }

    public void deletePathology(String pathologyID){
        PathologyMapper mapper=session.getMapper(PathologyMapper.class);
        mapper.deletePathology(pathologyID);
        session.commit();
    }

    public void updatePathology(Pathology pathology){
        PathologyMapper mapper=session.getMapper(PathologyMapper.class);
        mapper.updatePathology(pathology);
        session.commit();
    }

    /*
    Radiology
     */
    public List<Radiology> selectAllRadiologies(){
        try{
            RadiologyMapper mapper=session.getMapper(RadiologyMapper.class);
            return mapper.selectAllRadiologies();

        } finally {
            if(!keepAlive) close();
        }
    }
    /*
    File
     */
    public String getLink(String pid){
        try{
            FileMapper mapper = session.getMapper(FileMapper.class);
            return mapper.getLink(pid);
        } finally {
            if(!keepAlive) close();
        }
    }

    public Radiology selectOneRadiology(String radiologyID){
        RadiologyMapper mapper=session.getMapper(RadiologyMapper.class);
        return mapper.selectOneRadiology(radiologyID);
    }

    public void addRadiology(Radiology radiology){
        RadiologyMapper mapper=session.getMapper(RadiologyMapper.class);
        mapper.addRadiology(radiology);
        session.commit();
    }

    public void deleteRadiology(String radiologyID){
        RadiologyMapper mapper=session.getMapper(RadiologyMapper.class);
        mapper.deleteRadiology(radiologyID);
        session.commit();
    }

    public void updateRadiology(Radiology radiology){
        RadiologyMapper mapper=session.getMapper(RadiologyMapper.class);
        mapper.updateRadiology(radiology);
        session.commit();
    }



    /*
    Notification token
     */
    public void insertUserFcmToken(String uid, String fcmToken) {

        try {

            NotificationTokenMapper mapper = session.getMapper(NotificationTokenMapper.class);
            NotificationToken token = new NotificationToken().uid(uid).fcm_token(fcmToken);
            mapper.insertUserToken(token);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    public void deleteUserFcmToken(String uid, String fcmToken) {


        try {

            NotificationTokenMapper mapper = session.getMapper(NotificationTokenMapper.class);
            NotificationToken token = new NotificationToken().uid(uid).fcm_token(fcmToken);
            mapper.deleteUserToken(token);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    public ArrayList<String> getFcmTokenByUid(String uid) {

        try {

            NotificationTokenMapper mapper = session.getMapper(NotificationTokenMapper.class);
            return mapper.getTokensByUserId(uid);

        } finally {
            if (!keepAlive) close();
        }
    }

    public NotificationToken getUserFcmToken(String fcm_token) {

        try {

            NotificationTokenMapper mapper = session.getMapper(NotificationTokenMapper.class);
            return mapper.getUserByToken(fcm_token);

        } finally {
            if (!keepAlive) close();
        }
    }


    public void insertUser(User user) {
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.insertUser(user);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateUser(User user) {
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.updateUser(user);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void insertAppointment(Appointment appointment) {
        try {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            mapper.insertAppointment(appointment);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateAppointment(Appointment appointment) {
        try {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            mapper.updateAppointment(appointment);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void insertFile(File file) {
        try {
            FileMapper mapper = session.getMapper(FileMapper.class);
            mapper.insertFile(file);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public File selectFileById(String id) {
        try {
            FileMapper mapper = session.getMapper(FileMapper.class);
            return mapper.selectFileById(id);
        } finally {
            if(!keepAlive) close();
        }
    }

    @Override
    protected void finalize() {
        close();
    }

    public void close() {
        session.close();
    }

}