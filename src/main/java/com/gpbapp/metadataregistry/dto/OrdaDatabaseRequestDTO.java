package com.gpbapp.metadataregistry.dto;

import java.util.List;
import java.util.Map;

public class OrdaDatabaseRequestDTO {

    private String name;
    private String service;
    private List<String> dataProducts;
    private boolean isDefault;
    private String description;
    private String displayName;
    private String domain;
    private Map<String, Object> extension;
    private LifeCycle lifeCycle;
    private Owner owner;
    private String retentionPeriod;
    private String sourceHash;
    private String sourceUrl;
    private List<Tag> tags;

    public OrdaDatabaseRequestDTO() {
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public List<String> getDataProducts() { return dataProducts; }
    public void setDataProducts(List<String> dataProducts) { this.dataProducts = dataProducts; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    public Map<String, Object> getExtension() { return extension; }
    public void setExtension(Map<String, Object> extension) { this.extension = extension; }

    public LifeCycle getLifeCycle() { return lifeCycle; }
    public void setLifeCycle(LifeCycle lifeCycle) { this.lifeCycle = lifeCycle; }

    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }

    public String getRetentionPeriod() { return retentionPeriod; }
    public void setRetentionPeriod(String retentionPeriod) { this.retentionPeriod = retentionPeriod; }

    public String getSourceHash() { return sourceHash; }
    public void setSourceHash(String sourceHash) { this.sourceHash = sourceHash; }

    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public List<Tag> getTags() { return tags; }
    public void setTags(List<Tag> tags) { this.tags = tags; }

    // Вложенные DTO
    public static class LifeCycle {
        private Event accessed;
        private Event created;
        private Event updated;

        public Event getAccessed() { return accessed; }
        public void setAccessed(Event accessed) { this.accessed = accessed; }

        public Event getCreated() { return created; }
        public void setCreated(Event created) { this.created = created; }

        public Event getUpdated() { return updated; }
        public void setUpdated(Event updated) { this.updated = updated; }

        public static class Event {
            private Owner accessedBy;
            private String accessedByAProcess;
            private long timestamp;

            public Owner getAccessedBy() { return accessedBy; }
            public void setAccessedBy(Owner accessedBy) { this.accessedBy = accessedBy; }

            public String getAccessedByAProcess() { return accessedByAProcess; }
            public void setAccessedByAProcess(String accessedByAProcess) { this.accessedByAProcess = accessedByAProcess; }

            public long getTimestamp() { return timestamp; }
            public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
        }
    }

    public static class Owner {
        private boolean deleted;
        private String description;
        private String displayName;
        private String fullyQualifiedName;
        private String href;
        private String id;
        private String name;
        private String type;

        public boolean isDeleted() { return deleted; }
        public void setDeleted(boolean deleted) { this.deleted = deleted; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }

        public String getFullyQualifiedName() { return fullyQualifiedName; }
        public void setFullyQualifiedName(String fullyQualifiedName) { this.fullyQualifiedName = fullyQualifiedName; }

        public String getHref() { return href; }
        public void setHref(String href) { this.href = href; }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class Tag {
        private String description;
        private String displayName;
        private String href;
        private String labelType;
        private String name;
        private String source;
        private String state;
        private Style style;
        private String tagFQN;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }

        public String getHref() { return href; }
        public void setHref(String href) { this.href = href; }

        public String getLabelType() { return labelType; }
        public void setLabelType(String labelType) { this.labelType = labelType; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public Style getStyle() { return style; }
        public void setStyle(Style style) { this.style = style; }

        public String getTagFQN() { return tagFQN; }
        public void setTagFQN(String tagFQN) { this.tagFQN = tagFQN; }

        public static class Style {
            private String color;
            private String iconURL;

            public String getColor() { return color; }
            public void setColor(String color) { this.color = color; }

            public String getIconURL() { return iconURL; }
            public void setIconURL(String iconURL) { this.iconURL = iconURL; }
        }
    }
}


