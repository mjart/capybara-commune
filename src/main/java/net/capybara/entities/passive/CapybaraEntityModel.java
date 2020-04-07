package net.capybara.entities.passive;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.system.MathUtil;

@Environment(EnvType.CLIENT)
public class CapybaraEntityModel extends AnimalModel<CapybaraEntity> {
    private ModelPart head;
    private ModelPart ears;
    private ModelPart body;
    private ModelPart legBackLeft;
    private ModelPart legBackRight;
    private ModelPart legFrontLeft;
    private ModelPart legFrontRight;
    private ModelPart backrug;

    public CapybaraEntityModel() {
        //boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, float childBodyYOffset
        super(true, 2.0f,4F,3F,-1f, -1f);

        textureWidth = 128;
        textureHeight = 64;

        body = new ModelPart(this);
        body.setPivot(0.0F, 10.0F, 2.0F);
        forgeToFabricAddCuboid(body, 6, 9, -2.0F, 2.5F, -7.0F, 3, 4, 11, 1.75F, true);

        backrug = new ModelPart(this);
        backrug.setPivot(-0.45F, 2.45F, 4.25F);
        setRotationAngle(backrug, 0.2967F, 0.0F, 0.0F);
        body.addChild(backrug);
        forgeToFabricAddCuboid(backrug, 14, 3, -3.25F, -1.3617F, -0.5596F, 6, 4, 1, 0.01F, false);

        head = new ModelPart(this);
        head.setPivot(0.0F, 14.3333F, -7.0F);
        forgeToFabricAddCuboid(head, 78, 14, -4.0F, -4.8333F, -9.0F, 7, 6, 9, 0.6F, true);
        forgeToFabricAddCuboid(head, 105, 3, -4.0F, -3.8333F, -10.0F, 7, 4, 1, 0.0F, false);

        ears = new ModelPart(this);
        ears.setPivot(-0.5F, -5.28F, 0.6967F);
        setRotationAngle(ears, 2.0071F, 0.0F, 0.0F);
        head.addChild(ears);
        forgeToFabricAddCuboid(ears, 107, 2, 3.0F, -1.5F, -1.0F, 1, 3, 2, 0.0F, false);
        forgeToFabricAddCuboid(ears, 107, 2, -4.0F, -1.5F, -1.0F, 1, 3, 2, 0.0F, false);

        legBackLeft = new ModelPart(this);
        legBackLeft.setPivot(2.0F, 18.0F, 6.0F);
        forgeToFabricAddCuboid(legBackLeft, 41, 26, -1.0F, 0.5F, -1.0F, 1, 4, 1, 0.5F, true);
        forgeToFabricAddCuboid(legBackLeft, 98, 33, -1.5F, 5.0F, -2.25F, 2, 1, 2, 0.0F, false);

        legBackRight = new ModelPart(this);
        legBackRight.setPivot(-3.0F, 18.0F, 6.0F);
        forgeToFabricAddCuboid(legBackRight, 0, 16, 0.0F, 0.5F, -1.0F, 1, 4, 1, 0.5F, true);
        forgeToFabricAddCuboid(legBackRight, 98, 33, -0.5F, 5.0F, -2.25F, 2, 1, 2, 0.0F, false);

        legFrontLeft = new ModelPart(this);
        legFrontLeft.setPivot(2.0F, 18.0F, -4.0F);
        forgeToFabricAddCuboid(legFrontLeft, 41, 26, -1.0F, 0.5F, 0.0F, 1, 4, 1, 0.5F, true);
        forgeToFabricAddCuboid(legFrontLeft, 98, 33, -1.5F, 5.0F, -1.25F, 2, 1, 2, 0.0F, false);

        legFrontRight = new ModelPart(this);
        legFrontRight.setPivot(-3.0F, 18.0F, -4.0F);
        forgeToFabricAddCuboid(legFrontRight, 0, 16, 0.0F, 0.5F, 0.0F, 1, 4, 1, 0.5F, true);
        forgeToFabricAddCuboid(legFrontRight, 98, 33, -0.5F, 5.0F, -1.25F, 2, 1, 2, 0.0F, false);
    }

    public void setAngles(CapybaraEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        this.head.pitch = (float)Math.toRadians(headPitch);
        this.head.yaw = (float)Math.toRadians(headYaw);
//        this.body.pitch = 1.5707964F;
        this.legBackRight.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.legBackLeft.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.legFrontRight.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.legFrontLeft.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(body, legBackLeft, legBackRight, legFrontLeft, legFrontRight);
    }

    private static void forgeToFabricAddCuboid(ModelPart part, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, boolean mirror) {
        part.mirror = mirror;
        part.addCuboid(null, x, y, z, (int)dx, (int)dy, (int)dz, delta, (int)texU, (int)texV);
    }

    private static void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}
