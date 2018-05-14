package com.crm.application.utilModels;

import org.springframework.data.jpa.domain.Specification;
import com.crm.application.model.Client;

public class ClientSpecifications {

    public static Specification<Client> withClientType(String client_type) {
        return ((root, query, builder) -> builder.equal(root.get("client_type"), client_type));
    }

    public static Specification<Client> withEmail(String email) {
        return ((root, query, builder) -> builder.equal(root.get("email"), email));
    }

    public static Specification<Client> withPhone(String phone) {
        return ((root, query, builder) -> builder.equal(root.get("phone"), phone));
    }

    public static Specification<Client> withDescription(String description) {
        return ((root, query, builder) -> builder.equal(root.get("description"), description));
    }

    public static Specification<Client> withFirstName(String firstName) {
        return ((root, query, builder) -> builder.equal(root.get("firstName"), firstName));
    }

    public static Specification<Client> withLastName(String lastName) {
        return ((root, query, builder) -> builder.equal(root.get("lastName"), lastName));
    }

    public static Specification<Client> withPESEL(String pesel) {
        return ((root, query, builder) -> builder.equal(root.get("pesel"), pesel));
    }

    public static Specification<Client> withName(String name) {
        return ((root, query, builder) -> builder.equal(root.get("name"), name));
    }

    public static Specification<Client> withNip(String nip) {
        return ((root, query, builder) -> builder.equal(root.get("nip"), nip));
    }

    public static Specification<Client> withRegon(String regon) {
        return ((root, query, builder) -> builder.equal(root.get("regon"), regon));
    }

    public static Specification<Client> withType(String type) {
        return ((root, query, builder) -> builder.equal(root.get("type"), type));
    }

    public static Specification<Client> withWebsite(String website) {
        return ((root, query, builder) -> builder.equal(root.get("website"), website));
    }

    public static Specification<Client> withTrade(String trade) {
        return ((root, query, builder) -> builder.equal(root.get("trade"), trade));
    }
}
