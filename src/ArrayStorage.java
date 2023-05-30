import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int countResume = 0;

    void clear() {
        for (int i = 0; i < countResume - 1; i++) {
            storage[i] = null;
        }
        countResume = 0;
    }

    void save(Resume r) {
        countResume++;
        storage[countResume - 1] = r;

    }

    Resume get(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int indexForDel = -1;
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                indexForDel = i;
                break;
            }
        }
        if (indexForDel > -1) {
            for (int i = indexForDel + 1; i < countResume; i++) {
                storage[i - 1] = storage[i];
            }
            countResume--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    int size() {
        return countResume;
    }
}
