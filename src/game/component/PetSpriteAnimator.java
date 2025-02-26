package game.component;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles pet sprite animations from the provided sprite sheets
 */
public class PetSpriteAnimator {
    private Image spriteSheet;
    private int frameWidth;
    private int frameHeight;
    private int colorVariant; // 0=white, 1=brown, 2=yellow/golden, 3=black
    private Map<String, List<Image>> animations = new HashMap<>();
    private String currentAnimation = "idle_right";
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private long frameDurationInNanos = 200_000_000; // 200ms default
    
    // Direction constants
    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 3;
    
    /**
     * Creates a new pet sprite animator with the given sprite sheet.
     * 
     * @param spriteSheetPath Path to the sprite sheet image
     * @param frameWidth Width of each frame in pixels
     * @param frameHeight Height of each frame in pixels
     * @param colorVariant Color variant to use (0=white, 1=brown, 2=yellow/golden, 3=black)
     */
    public PetSpriteAnimator(String spriteSheetPath, int frameWidth, int frameHeight, int colorVariant) {
        this.spriteSheet = new Image(getClass().getResourceAsStream(spriteSheetPath));
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.colorVariant = colorVariant;
        
        // Extract all animations from the sprite sheet based on the known layout
        extractAnimations();
    }
    
    /**
     * Extract all animations from the sprite sheet based on the layout in the images
     */
    private void extractAnimations() {
        // Based on the sprite sheet layout:
        // - First row: Walk right (frames 0-3)
        // - Second row: Idle up/down view (frames 0-3)
        // - Third row: Walk down (frames 0-3)
        // - Fourth row: Sleep/rest pose (frames 0-3)
        // - Fifth row: Walk left (frames 0-3)
        // - Sixth row: Idle left/right view (frames 0-3)
        // - Seventh row: Walk up (frames 0-3)
        // - Eighth row: Additional poses (frames 0-3)
        
        // Each color variant has 4 frames horizontally
        int framesPerVariant = 4;
        int startX = colorVariant * framesPerVariant * frameWidth;
        
        // Walking animations (4 frames each)
        addAnimationFrames("walk_right", startX, 0 * frameHeight, 4);
        addAnimationFrames("walk_down", startX, 2 * frameHeight, 4);
        addAnimationFrames("walk_left", startX, 4 * frameHeight, 4);
        addAnimationFrames("walk_up", startX, 6 * frameHeight, 4);
        
        // Idle animations
        addAnimationFrames("idle_updown", startX, 1 * frameHeight, 4);
        addAnimationFrames("idle_rightleft", startX, 5 * frameHeight, 4);
        
        // Sleep/rest pose
        addAnimationFrames("sleep", startX, 3 * frameHeight, 4);
        
        // Additional poses
        addAnimationFrames("special", startX, 7 * frameHeight, 4);
        
        // Create direction-specific idle animations from the generic ones
        copyAnimation("idle_updown", "idle_up");
        copyAnimation("idle_updown", "idle_down");
        copyAnimation("idle_rightleft", "idle_right");
        copyAnimation("idle_rightleft", "idle_left");
    }
    
    /**
     * Adds frames for an animation from the sprite sheet
     */
    private void addAnimationFrames(String name, int startX, int startY, int frameCount) {
        List<Image> frames = new ArrayList<>();
        
        for (int i = 0; i < frameCount; i++) {
            WritableImage frame = new WritableImage(
                    spriteSheet.getPixelReader(),
                    startX + (i * frameWidth),
                    startY,
                    frameWidth,
                    frameHeight
            );
            frames.add(frame);
        }
        
        animations.put(name, frames);
    }
    
    /**
     * Creates a copy of an animation with a new name
     */
    private void copyAnimation(String sourceName, String targetName) {
        if (animations.containsKey(sourceName)) {
            animations.put(targetName, new ArrayList<>(animations.get(sourceName)));
        }
    }
    
    /**
     * Sets the animation to play based on direction and action
     * 
     * @param action The action (walk, idle, sleep)
     * @param direction The direction (0=right, 1=down, 2=left, 3=up)
     */
    public void setAnimation(String action, int direction) {
        String directionName;
        switch (direction) {
            case DIRECTION_RIGHT: directionName = "right"; break;
            case DIRECTION_DOWN: directionName = "down"; break;
            case DIRECTION_LEFT: directionName = "left"; break;
            case DIRECTION_UP: directionName = "up"; break;
            default: directionName = "right";
        }
        
        String animationName = action + "_" + directionName;
        if (animations.containsKey(animationName) && !animationName.equals(currentAnimation)) {
            currentAnimation = animationName;
            currentFrame = 0;
        }
    }
    
    /**
     * Sets the frame duration (animation speed)
     * 
     * @param duration Duration in milliseconds for each frame
     */
    public void setFrameDuration(double duration) {
        this.frameDurationInNanos = (long) (duration * 1_000_000);
    }
    
    /**
     * Updates the animation and returns the current frame image
     * 
     * @param now Current time in nanoseconds (from AnimationTimer)
     * @return The current frame image
     */
    public Image update(long now) {
        if (currentAnimation == null || animations.isEmpty() || !animations.containsKey(currentAnimation)) {
            return null;
        }
        
        List<Image> frames = animations.get(currentAnimation);
        if (frames.isEmpty()) {
            return null;
        }
        
        // Update frame if enough time has passed
        if (now - lastFrameTime >= frameDurationInNanos) {
            currentFrame = (currentFrame + 1) % frames.size();
            lastFrameTime = now;
        }
        
        return frames.get(currentFrame);
    }
    
    /**
     * Gets the current frame without updating animation timing
     * 
     * @return The current frame image
     */
    public Image getCurrentFrame() {
        if (currentAnimation == null || !animations.containsKey(currentAnimation)) {
            return null;
        }
        List<Image> frames = animations.get(currentAnimation);
        if (frames.isEmpty()) {
            return null;
        }
        return frames.get(currentFrame);
    }
    
    /**
     * Gets the width of a single frame
     * 
     * @return Frame width in pixels
     */
    public int getFrameWidth() {
        return frameWidth;
    }
    
    /**
     * Gets the height of a single frame
     * 
     * @return Frame height in pixels
     */
    public int getFrameHeight() {
        return frameHeight;
    }
    
    /**
     * Changes the color variant of the pet
     * 
     * @param colorVariant The color variant (0=white, 1=brown, 2=yellow/golden, 3=black)
     */
    public void setColorVariant(int colorVariant) {
        if (this.colorVariant != colorVariant) {
            this.colorVariant = colorVariant;
            extractAnimations(); // Re-extract animations for the new color
        }
    }
}