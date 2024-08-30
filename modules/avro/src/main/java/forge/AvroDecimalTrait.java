package forge;

import software.amazon.smithy.model.SourceException;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AbstractTrait;
import software.amazon.smithy.model.traits.AbstractTraitBuilder;
import software.amazon.smithy.model.traits.Trait;
import software.amazon.smithy.utils.ToSmithyBuilder;

import java.util.Objects;

public class AvroDecimalTrait extends AbstractTrait implements ToSmithyBuilder<AvroDecimalTrait> {


    public static ShapeId ID = ShapeId.from("forge#avroDecimal");

    // must be 0 or greater
    private final Integer precision ;
    private final Integer scale ;
    private final Underlying underlying ;

   public enum Underlying {
        BYTES,
        FIXED
    }


    @Override
    protected Node createNode() {
        return Node.objectNodeBuilder()
                .sourceLocation(getSourceLocation())
                .withMember("precision", Node.from(precision))
                .withMember("scale", Node.from(scale))
                .withMember("underlying", Node.from(underlying.toString()))
                .build();
    }


    public Integer getPrecision() {
        return precision;
    }

    public Integer getScale() {
        return scale;
    }

    public Underlying getUnderlying() {
        return underlying;
    }



    private AvroDecimalTrait(AvroDecimalTrait.Builder builder) {
        super(ID, builder.getSourceLocation());
        this.precision = Objects.requireNonNull(builder.precision, "precision must be defined");
        this.scale = Objects.requireNonNull(builder.scale, "scale must be defined");
        this.underlying = builder.underlying;
      if (precision <= 0 || scale <= 0) {
            throw new SourceException("precision and scale must be greater than 0", getSourceLocation());
        }
    }

    public static AvroDecimalTrait.Builder builder() {
        return new AvroDecimalTrait.Builder();
    }

    @Override
    public  AvroDecimalTrait.Builder toBuilder() {
        return new AvroDecimalTrait.Builder().sourceLocation(getSourceLocation()).precision(precision).scale(scale).underlying(underlying);
    }

    public static final class Builder extends AbstractTraitBuilder<AvroDecimalTrait, AvroDecimalTrait.Builder> {
        private Integer precision;
        private Integer scale;
        private Underlying underlying = Underlying.BYTES;

        public AvroDecimalTrait.Builder precision(Integer precision) {
            this.precision = precision;
            return this;
        }

        public AvroDecimalTrait.Builder scale(Integer scale) {
            this.scale = scale;
            return this;
        }

        public AvroDecimalTrait.Builder underlying(Underlying underlying) {
            this.underlying = underlying;
            return this;
        }


        @Override
        public AvroDecimalTrait build() {
            return new AvroDecimalTrait(this);
        }
    }


    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            AvroDecimalTrait.Builder builder = builder().sourceLocation(value);

            value.expectObjectNode()
                    .expectNumberMember("scale", n -> builder.scale(n.intValue()))
                    .expectNumberMember("method", n -> builder.precision(n.intValue()))
                    .getStringMember("underlying", (underlying) -> {
                        switch(underlying.toLowerCase())
                        {
                            case "bytes":
                                builder.underlying(Underlying.BYTES);
                                break;
                            case "fixed":
                                builder.underlying(Underlying.FIXED);
                                break;
                            default:
                                throw new SourceException("underlying must be either bytes or fixed", value.getSourceLocation());
                        }
                    });
            return builder.build();
        }
    }
}
