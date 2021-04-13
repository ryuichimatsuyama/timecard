package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "messages")
//相手と自分のみのメッセージのみ
@NamedQuery(name = "getOurMessages", query = "select e from Message as e where (e.send=:send and e.get = :get) or (e.send=:get and e.get=:send) ORDER BY e.created_at DESC")
@Entity
public class Message {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getSend() {
        return send;
    }

    public void setSend(Employee send) {
        this.send = send;
    }

    public Employee getGet() {
        return get;
    }

    public void setGet(Employee get) {
        this.get = get;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //送信者
    @ManyToOne
    @JoinColumn(name = "send_id", nullable = false)
    private Employee send;
    //受信者
    @ManyToOne
    @JoinColumn(name = "get_id", nullable = false)
    private Employee get;
    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
