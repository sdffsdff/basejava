import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int countResume = -1;

    void clear() {
        for (int i = 0; i < countResume; i++) {
            storage[i] = null;
        }
        countResume = -1;
    }

    void save(Resume r) {
        countResume++;
        storage[countResume] = r;

    }

    Resume get(String uuid) {
        for (int i = 0; i <= countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int deletedcountResume = -1;
        for (int i = 0; i <= countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                deletedcountResume = i;
                break;
            }
        }
        if (deletedcountResume != -1) {
            for (int i = deletedcountResume + 1; i <= countResume; i++) {
                storage[i - 1] = storage[i];
            }
            countResume--;
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResume + 1);
    }

    int size() {
        return countResume + 1;
    }
}
