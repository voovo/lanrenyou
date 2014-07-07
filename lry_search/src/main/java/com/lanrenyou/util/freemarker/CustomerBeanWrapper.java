package com.lanrenyou.util.freemarker;

import freemarker.core.CollectionAndSequence;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.MapModel;
import freemarker.ext.util.ModelFactory;
import freemarker.template.*;

import java.util.Map;
import java.util.Set;

public class CustomerBeanWrapper extends BeansWrapper {
    private boolean altMapWrapper;

    public CustomerBeanWrapper() {
        this.altMapWrapper = true;
    }

    protected ModelFactory getModelFactory(Class clazz) {
        // attempt to get the best of both the SimpleMapModel and the
        // MapModel of FM.
        if (altMapWrapper && Map.class.isAssignableFrom(clazz)) {
            return FriendlyMapModel.FACTORY;
        }

        return super.getModelFactory(clazz);
    }

    /**
     * Attempting to get the best of both worlds of FM's MapModel and
     * SimpleMapModel, by reimplementing the isEmpty(), keySet() and
     * values() methods. ?keys and ?values built-ins are thus available,
     * just as well as plain Map methods.
     */
    private static final class FriendlyMapModel extends MapModel implements
            TemplateHashModelEx {
        static final ModelFactory FACTORY = new ModelFactory() {
            public TemplateModel create(Object object, ObjectWrapper wrapper) {
                return new FriendlyMapModel((Map) object,
                        (BeansWrapper) wrapper);
            }
        };

        public FriendlyMapModel(Map map, BeansWrapper wrapper) {
            super(map, wrapper);
        }

        public boolean isEmpty() {
            return ((Map) object).isEmpty();
        }

        protected Set keySet() {
            return ((Map) object).keySet();
        }

        public TemplateCollectionModel values() {
            return new CollectionAndSequence(new SimpleSequence(
                    ((Map) object).values(), wrapper));
        }
    }
}