package com.eazyBank.cards.services;

import com.eazyBank.cards.dtos.CardsDto;

public interface ICardsServices {
    /**
     * The method for creating card
     * @param mobileNumber customers mobile number
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber customers mobile number
     * @return returns the card details
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     * can update the card but you can't change card number or mobile number
     * @param cardsDto take card details as input
     * @return true if deleted successfully
     */
    Boolean updateCard(CardsDto cardsDto);

    /**
     *
     * @param mobileNumber delete the card by mobile number
     * @return true if deleted successfully else return false
     */
    Boolean deleteCard(String mobileNumber);
}
