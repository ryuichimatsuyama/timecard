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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "approvals")
@NamedQueries({
		@NamedQuery(name = "getMyApprovalCount", query = "SELECT COUNT(m) FROM Approval AS m where m.card.employee=:card"),
		@NamedQuery(name = "getMyAllApproved", query = "SELECT m FROM Approval AS m where m.card.employee=:card ORDER BY m.id DESC") })

@Entity
public class Approval {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "card_id", nullable = false)
	private Card card;

	@Column(name = "approved_at", nullable = false)
	private Timestamp approved_at;

	@Lob
	@Column(name = "review", nullable = false)
	private String review;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Timestamp getApproved_at() {
		return approved_at;
	}

	public void setApproved_at(Timestamp approved_at) {
		this.approved_at = approved_at;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

}
