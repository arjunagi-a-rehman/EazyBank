package com.eazyBank.cards.services.Implimentations;

import com.eazyBank.cards.constants.CardsConstants;
import com.eazyBank.cards.dtos.CardsDto;
import com.eazyBank.cards.exception.CardAlreadyExistException;
import com.eazyBank.cards.exception.ResourceNotFoundException;
import com.eazyBank.cards.mapper.CardMapper;
import com.eazyBank.cards.models.Cards;
import com.eazyBank.cards.repository.ICardsRepo;
import com.eazyBank.cards.services.ICardsServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class CardServices implements ICardsServices {
    private ICardsRepo cardsRepo;

    /**
     * The method for creating card
     *
     * @param mobileNumber customers mobile number
     */
    @Override
    public void createCard(String mobileNumber) {
        if(cardsRepo.findByMobileNumber(mobileNumber).isPresent()){
            throw new CardAlreadyExistException("The card with following mobile number: "+mobileNumber+" already exist");
        }
        cardsRepo.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber customers mobile number
     * @return returns the card details
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card=cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Card","Mobile Number",mobileNumber));
        return CardMapper.cardsToCardDtoMapper(card,new CardsDto());
    }

    /**
     * can update the card  you can't change card number or mobile number
     *
     * @param cardsDto take card details as input
     * @return true if deleted successfully
     */
    @Override
    public Boolean updateCard(CardsDto cardsDto) {
        Cards card=cardsRepo.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(()->new ResourceNotFoundException("Card","Card Number",cardsDto.getCardNumber()));
        cardsDto.setMobileNumber(card.getMobileNumber());
        cardsRepo.save(CardMapper.cardDtoToCardMapper(cardsDto,card));
        return true;
    }

    /**
     * @param mobileNumber delete the card by mobile number
     * @return true if deleted successfully else return false
     */
    @Override
    public Boolean deleteCard(String mobileNumber) {
        Cards card=cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Card","Mobile Number",mobileNumber));
        cardsRepo.delete(card);
        return true;
    }

    private Cards createNewCard(String mobileNumber){
        Cards cards=new Cards();
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setMobileNumber(mobileNumber);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        cards.setCardNumber(Long.toString(randomCardNumber));
        return cards;
    }
}
