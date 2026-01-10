import java.util.*;

public class ProRecommendationSystem {

    // User -> (Item -> Rating)
    static Map<String, Map<String, Integer>> userRatings = new HashMap<>();

    public static void main(String[] args) {

        loadSampleData();

        String targetUser = "User1";

        System.out.println("Target User: " + targetUser);
        System.out.println("Recommended Items:");

        List<String> recommendations = recommendItems(targetUser);

        for (String item : recommendations) {
            System.out.println("- " + item);
        }
    }

    // Load sample dataset
    private static void loadSampleData() {
        userRatings.put("User1", Map.of(
                "Java Course", 5,
                "AI Course", 4
        ));

        userRatings.put("User2", Map.of(
                "Java Course", 4,
                "Python Course", 5,
                "AI Course", 5
        ));

        userRatings.put("User3", Map.of(
                "Python Course", 4,
                "Web Development", 5
        ));
    }

    // Recommendation logic
    private static List<String> recommendItems(String targetUser) {
        Map<String, Integer> targetRatings = userRatings.get(targetUser);
        Map<String, Double> scores = new HashMap<>();

        for (String otherUser : userRatings.keySet()) {
            if (otherUser.equals(targetUser)) continue;

            double similarity = calculateSimilarity(
                    targetRatings,
                    userRatings.get(otherUser)
            );

            for (String item : userRatings.get(otherUser).keySet()) {
                if (!targetRatings.containsKey(item)) {
                    scores.put(item,
                            scores.getOrDefault(item, 0.0)
                                    + similarity * userRatings.get(otherUser).get(item)
                    );
                }
            }
        }

        List<String> recommendedItems = new ArrayList<>(scores.keySet());
        recommendedItems.sort((a, b) -> Double.compare(scores.get(b), scores.get(a)));

        return recommendedItems;
    }

    // Cosine similarity
    private static double calculateSimilarity(
            Map<String, Integer> user1,
            Map<String, Integer> user2
    ) {
        double dot = 0, mag1 = 0, mag2 = 0;

        for (String item : user1.keySet()) {
            if (user2.containsKey(item)) {
                dot += user1.get(item) * user2.get(item);
            }
            mag1 += Math.pow(user1.get(item), 2);
        }

        for (int rating : user2.values()) {
            mag2 += Math.pow(rating, 2);
        }

        if (mag1 == 0 || mag2 == 0) return 0;
        return dot / (Math.sqrt(mag1) * Math.sqrt(mag2));
    }
}
