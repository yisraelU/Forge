package forge;


import software.amazon.smithy.model.SourceException;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AbstractTrait;
import software.amazon.smithy.model.traits.AbstractTraitBuilder;
import software.amazon.smithy.model.traits.Trait;
import software.amazon.smithy.utils.ToSmithyBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AvroFixed extends AbstractTrait implements ToSmithyBuilder<AvroFixed> {


    public static ShapeId ID = ShapeId.from("forge#avroFixed");


    private final int size;
    private final String namespace;
    private final String name;

    private final List<String> aliasList;



    @Override
    protected Node createNode() {
        return Node.objectNodeBuilder()
                .sourceLocation(getSourceLocation())
                .withMember("size", Node.from(size))
                .withMember("namespace", Node.from(namespace))
                .withMember("name", Node.from(name))
                .withMember("aliases", Node.fromStrings(aliasList))
                .build();
    }

    public int getSize() {
        return size;
    }

    public Optional<String> getNamespace() {
        return Optional.ofNullable(namespace);
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliasList;
    }



    private AvroFixed(AvroFixed.Builder builder) {
        super(ID, builder.getSourceLocation());
            this.size = Objects.requireNonNull(builder.size, "size must be defined");
            this.namespace = builder.namespace.orElse(null);
            this.name = builder.name;
            this.aliasList = builder.aliasList;
            if (size <= 0) {
                throw new SourceException("size must be greater than 0", getSourceLocation());
            }

    }

    public static AvroFixed.Builder builder() {
        return new AvroFixed.Builder();
    }

    @Override
    public  AvroFixed.Builder toBuilder() {
        return new AvroFixed.Builder()
                .sourceLocation(getSourceLocation())
                .size(size)
                .namespace(namespace)
                .name(name)
                .aliases(aliasList);
    }

    public static final class Builder extends AbstractTraitBuilder<AvroFixed, AvroFixed.Builder> {
        private Integer size;
        private Optional<String> namespace;
        private String name;
        private List<String> aliasList;

        public AvroFixed.Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public AvroFixed.Builder namespace(String namespace) {
            this.namespace = Optional.ofNullable(namespace);
            return this;
        }

        public AvroFixed.Builder name(String name) {
            this.name = name;
            return this;
        }

        public AvroFixed.Builder aliases(List<String> aliasList) {
            this.aliasList = aliasList;
            return this;
        }


        @Override
        public AvroFixed build() {
            return new AvroFixed(this);
        }
    }


    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            AvroFixed.Builder builder = builder().sourceLocation(value);

            ObjectNode node = value.expectObjectNode();
            int size = node.expectNumberMember("size").getValue().intValue();
            String namespace = node.getStringMember("namespace").map(StringNode::getValue).orElse(null);
            String name = node.expectStringMember("name").getValue();
            List<String> aliasList = node.expectArrayMember("aliases").getElementsAs(StringNode::getValue);
            builder.size(size).namespace(namespace).name(name).aliases(aliasList);
            return builder.build();
        }
    }
}
