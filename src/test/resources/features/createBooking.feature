@CreateBooking
Feature:Hotel Booking
  As a user
  I want to create a new booking
  So that I can book a hotel

  Background: Create Aut Token

  Scenario:Creates a new booking
    Given Creates a new booking, then verify response and status code 200

