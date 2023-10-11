@CreateBooking
Feature:Hotel Booking

  Background: Create Aut Token

  Scenario:Creates a new booking
    Given Creates a new booking, then verify response and status code 200
