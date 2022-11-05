package models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "times")
@NamedQueries({
        // 自分の承認されたカード
        @NamedQuery(name = "getMyApprovedTimes", query = "SELECT r FROM Card AS r where r.status=0 and r.employee =:employee ORDER BY r.work_date DESC"),
        // 自分の全てのカード
        @NamedQuery(name = "getMyAllTimes", query = "SELECT r FROM Card AS r where r.employee=:employee ORDER BY r.id DESC"),
        @NamedQuery(name = "getMyTimesCount", query = "SELECT COUNT(r) FROM Card AS r where r.employee=:employee"),
        // 自分宛のカード
        @NamedQuery(name = "getBossCards", query = "SELECT r FROM Card AS r WHERE r.boss = :boss  ORDER BY r.id DESC"),
        @NamedQuery(name = "getBossCount", query = "SELECT COUNT(r) FROM Card AS r WHERE r.boss = :boss ")

})

@Entity

public class Card {

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	//作成者
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

	@Column(name = "work_date", nullable = false)
    private Date work_date;
    //	承認者
    @OneToOne
    @JoinColumn(name = "boss_id")
    private Employee boss;

    @Column(name = "status")
    private Integer status;

    @Column(name = "start", nullable = false)
    private String start;

    @Column(name = "end")
    private String end;

    private String work_minutes;

    private long wage;

    public long getWage() {
        return wage;
    }

    public void setWage(long wage) {
        this.wage = wage;
    }

    public String getWork_minutes() {
        return work_minutes;
    }

    public void setWork_minutes(String work_minutes) {
        this.work_minutes = work_minutes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}