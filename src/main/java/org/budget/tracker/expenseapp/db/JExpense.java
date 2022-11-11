package org.budget.tracker.expenseapp.db;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@TypeDef(name = "json", typeClass = JsonType.class)
public class JExpense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Double cost;

  private String name;

  @Column(name = "category")
  private String category;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "paid_by")
  private String paidBy;

  @Column(name = "budget_id")
  private Integer budgetId;

//  @Column(name = "user_defined")
//  private boolean userDefined;

  @Column(name = "created_on")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdOn;

  @Column(name = "updated_on")
  private LocalDateTime updatedOn;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(String paidBy) {
    this.paidBy = paidBy;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }

  public Integer getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(Integer budgetId) {
    this.budgetId = budgetId;
  }

//  public void setUserDefined(boolean userDefined) {
//    this.userDefined = userDefined;
//  }
//
//  public boolean isUserDefined() {
//    return userDefined;
//  }
}
