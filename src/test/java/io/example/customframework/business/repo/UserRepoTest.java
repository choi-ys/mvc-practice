package io.example.customframework.business.repo;

import static org.assertj.core.api.Assertions.assertThat;

import io.example.customframework.business.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Repo:User")
class UserRepoTest {
    @Test
    @DisplayName("회원 저장")
    void saveUser() {
        // Given
        final String userId = "userId";
        final String name = "name";
        User given = User.of(userId, name);

        // When
        UserRepo.save(given);

        // Then
        assertThat(UserRepo.findById(given.getUserId())).isEqualTo(given);
    }
}
