package ru.geekbrains.lesson8.models;

import ru.geekbrains.lesson8.presenters.Model;

import java.util.*;

public class TableModel implements Model {


    private Collection<Table> tables;

    /**
     * Получение списка всех столиков
     */
    public Collection<Table> loadTables(){

        if (tables == null){
            tables = new ArrayList<>();

            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
        }

        return tables;
    }

    /**
     * Бронирование столика
     * @param reservationDate Дата бронирования
     * @param tableNo Номер столика
     * @param name Имя
     */
    @Override
    public int reservationTable(Date reservationDate, int tableNo, String name) {
        for (Table table: tables) {
            if (table.getNo() == tableNo){
                Reservation reservation = new Reservation(table, reservationDate, name);
                table.getReservations().add(reservation);
                return reservation.getId();
            }
        }
        throw new RuntimeException("Ошибка бронирования столика. Повторите попытку позже.");
    }

    /**
     * TODO: Доработать самостоятельнов рамках домашней работы
     * @param oldReservation
     * @param reservationDate
     * @param tableNo
     * @param name
     * @return
     */
    public int changeReservationTable(int oldReservation, Date reservationDate, int tableNo, String name){
        Reservation reservation = findReservation(oldReservation);
        reservation.getTable().getReservations().remove(reservation);
        Table reservedTable = findTable(tableNo);


        if (reservation != null && reservedTable != null) {
            reservation.setDate(reservationDate);
            reservation.setTable(reservedTable);
            reservedTable.getReservations().add(reservation);
            return reservation.getId();
        }
        return -1;
    }

    public Reservation findReservation(int idReservation){
        return tables.stream()
                .flatMap(x -> x.getReservations().stream())
                .filter(res -> res.getId() == idReservation)
                .findAny().orElse(null);
    }

    public Table findTable(int numberTable) {
        return tables.stream()
                .filter(table -> table.getNo() == numberTable)
                .findAny().orElse(null);
    }

}