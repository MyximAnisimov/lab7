package server;
import com.google.common.collect.Iterables;
import common.collections.Person;
import common.utility.CustomConsole;
import common.utility.ProductComparator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, управляющий коллекцией
 */

public class CollectionManager {
    public Deque<Person> collection;
    private LocalDate creationDate;

    public CollectionManager(DBhandler dbhandler) throws SQLException {
creationDate=LocalDate.now();
collection = dbhandler.downloadCurrentCollection();

    }

    /**
     * @return дата создания коллекции
     */
    public LocalDate getCreationDate(){
        return creationDate;
    }

    /**
     * @return коллекция
     */
    public Deque<Person> getCollection(){
        return collection;
    }
    /**
     * Функция, удаляющая первый элемент коллекции
     */
    public void removeFirst(){
        collection.removeFirst();
    }

    /**
     *Добавляет новый элемент коллекции класса Person
     * @param person новый элемент класса Person
     */
    public void addToCollection(Person person){

        collection.add(person);
    }

    /**
     * Функция, возвращающая элемент класса Person по его id
     * @param id поле id класса Person
     * @return элемент класса Person (null, если элемент с данным id не найден)
     */
    public Person getById(int id){
        for (Person person: collection) {
            if(person.getID() == id) return person;
        }
        return null;
    }

    /**
     * Функция. Удаляет элемент коллекции
     * @param person элемент коллекции, который необходимо удалить
     */
    public void removeElement(Person person){
        collection.remove(person);
    }


    /**
     * Функция. Подсчитывает количество элементов коллекции,
     * у которых поле name класса location меньше, чем аргумент функции
     * @param location_name Название локации, с которой сравнивается поля name класса location
     */
    public int countElementsLessThanLocation(String location_name){
        int counter=0;
      for(int i=0;i<collection.size();i++){
          if(collection.element().getLocation().getName().length()<location_name.length()){
              counter++;
          }
        }
          return counter;
    }

    /**
     * Очищает коллекцию
     */
    public void clearCollection(){
        this.collection.clear();
    }


    /**
     * @return коллекция.
     */
    public Queue<Person> get() {
        return collection;
    }


    /**
     * @return Имя типа коллекции.
     */
    public String type() {
        return collection.getClass().getName();
    }

    /**
     * @return Размер коллекции.
     */
    public int size() {
        return collection.size();
    }

    /**
     * @return Первый элемент коллекции (null если коллекция пустая).
     */
    public Person first() {
        if (collection.isEmpty()) return null;
        return sorted().get(0);
    }

    /**
     * @return Последний элемент коллекции (null если коллекция пустая).
     */
    public Person last() {
        if (collection.isEmpty()) return null;
        return Iterables.getLast(sorted());
    }

    /**
     * @return Отсортированная коллекция.
     */
    public List<Person> sorted() {
        return new ArrayList<>(collection)
                .stream()
                .sorted(new ProductComparator())
                .collect(Collectors.toList());
    }
    public List<Person> getSorted(){
        return this.get().stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());}


    /**
     * @param id ID элемента.
     * @return Проверяет, существует ли элемент с таким ID.
     */
    public boolean checkExist(int id) {
        return getById(id) != null;
    }


    /**
     * Добавляет элемент в коллекцию
     * @param element Элемент для добавления.
     * @return id нового элемента
     */
    public void add(Person element) {
      collection.add(element);
    }

    /**
     * Удаляет элемент из коллекции.
     * @param id ID элемента для удаления.
     */
    public void remove(int id) {
        for(Person person: this.getCollection()){
            if(person.getID()==id){
                this.removeElement(person);
            }
    }}



}


