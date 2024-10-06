package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Restaurant;

 public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	public Page<Restaurant> findByVenueNameLike(String keyword, Pageable pageable);
	public Page<Restaurant> findByCategoryId(Integer categoryId, Pageable pageable);
//	 public Page<Restaurant> findByCategoryLike(String categoryName, Pageable pageable);
	@Query("SELECT r FROM Restaurant r WHERE r.venueName LIKE %:keyword% OR r.description LIKE %:keyword% ORDER BY r.budgetRange ASC")
	Page<Restaurant> findByVenueNameOrDescriptionLikeOrderByBudgetRangeAsc(@Param("keyword") String keyword, Pageable pageable);
	@Query("SELECT r FROM Restaurant r WHERE r.venueName LIKE %:keyword% OR r.description LIKE %:keyword% ORDER BY r.budgetRange DESC")
	Page<Restaurant> findByVenueNameOrDescriptionLikeOrderByBudgetRangeDesc(@Param("keyword") String keyword, Pageable pageable);
	@Query("SELECT r FROM Restaurant r WHERE r.venueName LIKE %:keyword% OR r.description LIKE %:keyword% ORDER BY r.createdAt DESC")
	Page<Restaurant> findByVenueNameOrDescriptionLikeOrderByCreatedAtDesc(@Param("keyword") String keyword, Pageable pageable);
	public Page<Restaurant> findByCategoryIdOrderByBudgetRangeAsc(Integer categoryId, Pageable pageable);
	public Page<Restaurant> findByCategoryIdOrderByBudgetRangeDesc(Integer categoryId, Pageable pageable);
	public Page<Restaurant> findByCategoryIdOrderByCreatedAtDesc (Integer categoryId, Pageable pageable);
	public Page<Restaurant> findByBudgetRangeOrderByBudgetRangeAsc(Integer budgetrange, Pageable pageable); 
    public Page<Restaurant> findByBudgetRangeOrderByBudgetRangeDesc(Integer budgetrange, Pageable pageable); 
    public Page<Restaurant> findByBudgetRangeOrderByCreatedAtDesc(Integer budgetrange, Pageable pageable); 
    public Page<Restaurant> findAllByOrderByBudgetRangeAsc(Pageable pageable);
    public Page<Restaurant> findAllByOrderByBudgetRangeDesc(Pageable pageable);  
    public Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable); 
	public Restaurant getRestaurantById(Integer id);
//	public Page<Restaurant> findAllByOrderByReviewCountDesc(Pageable pageable); 
//	public Page<Restaurant> findAllByOrderByAverageScoreDesc(Pageable pageable);
 }