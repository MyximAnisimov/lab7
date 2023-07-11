package server.commands;

import common.utility.Visibility;

/**
 * Абстрактный класс, имеет поле с именем и описанием команды
 */
    public abstract class AbstractCommand implements Command{

        private final String name;
        private final String description;
        public Visibility visibility;


        public AbstractCommand(String name, String description, Visibility visibility) {
            this.name = name;
            this.description = description;
            this.visibility=visibility;
        }
        public Visibility getVisibility(){
            return visibility;
        }
        @Override
        public String getName() {
            return name;
        }
        @Override
        public String getDescription() {
            return description;
        }
    }

