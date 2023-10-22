@DeleteBooking
Feature:Delete Booking
  As a user
  I want to delete a booking
  So that I can delete a booking

  Scenario:Delete Booking
    Given Delete the Booking by id which is created, then verify the status code is 201
      And Get Booking Information By Id which is deleted, then verify response and status code is 404