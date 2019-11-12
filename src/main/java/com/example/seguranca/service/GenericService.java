package com.example.seguranca.service;



import com.example.seguranca.modal.AbstractModel;
import com.example.seguranca.repository.IGenericRepository;
import com.example.seguranca.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericService<T extends AbstractModel> {

    @Autowired
    protected IGenericRepository<T> repository;
    
    protected String[] propertiesToReturn;

    public T save(T entity) {
        return repository.save(entity);
    }

    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public void delete(List<? extends T> entities) {
        repository.deleteAll(entities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public <S extends T> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    public Page<T> findAll(T example, Pageable pageable) {
        return findAll(Example.of(example, getMatcher()), pageable);
    }

    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public List<T> findAll(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public Page<T> pageFindBy(T entity, Integer page, Integer size) {
        setProperties(entity.getPropertiesReturn());
        Pageable pageable = getPageable(entity, page, size);

        return buildEntityToJson(page, size, findAll(Example.of(entity, this.getMatcher()), pageable));
    }

    protected Pageable getPageable(T entity, Integer page, Integer size) {
        Sort.Direction direction = Optional.ofNullable(entity.getAscendingSort()).orElse(Boolean.TRUE) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String[] sort = (String[]) validateSort(entity.getSort()).toArray(new String[0]);
        return getPageable(page, size, direction, sort);
    }

    private List<String>  validateSort(List<String> sort) {
        if (Objects.isNull(sort) || sort.stream().anyMatch(Objects::isNull) || sort.isEmpty()) {
            sort = Collections.singletonList("id");
        }
        return sort;
    }

    public <S extends T> long count(Example<S> example) {
        return repository.count();
    }

    public <S extends T> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    protected Page<T> buildEntityToJson(Integer currentPage, Integer size, Page<T> page) {
        return createPage(
            page.getContent().stream().map(this::buildEntityToJson).collect(Collectors.toList()),
            getPageable(currentPage, size),
            page.getTotalElements()
        );
    }

    protected List<T> buildEntityToJson(List<T> list) {
        return list.stream()
            .map(this::buildEntityToJson)
            .collect(Collectors.toList());
    }

    protected T buildEntityToJson(T entity) {
        try {
            getProperties();
            return (T) EntityUtil.createNewInstanceWithProperties(entity, propertiesToReturn);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected Page<T> createPage(List<T> unities, Pageable pageable, long totalElements) {
        return new PageImpl<>(unities, pageable, totalElements);
    }

    protected Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(page < 1 ? page : page - 1, size < 1 ? 1 : size);
    }

    protected Pageable getPageable(Integer page, Integer size, Sort sort) {
        return PageRequest.of(page < 1 ? page : page - 1, size < 1 ? 1 : size, sort);
    }

    protected Pageable getPageable(Integer page, Integer size, Sort.Direction direction, String ...properties) {
        return PageRequest.of(page < 1 ? page : page - 1, size < 1 ? 1 : size, Sort.by(direction, properties));
    }
    
    protected ExampleMatcher getMatcher() {
        return ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    }
    
    protected void getProperties(){
        propertiesToReturn = new String[]{ "id" };
    }

    protected void setProperties (List<String> properties) {
        if (Objects.nonNull(properties)) {
            propertiesToReturn = properties.toArray(new String[0]);
        }
    }

}
