@GetBookingById
Feature:Hotel Booking
  As a user
  I want to get booking information by id
  So that I can get booking information

  Scenario:Get Booking Information By Id
    Given Get Booking Information By Id which is crated, then verify response and status code is 200