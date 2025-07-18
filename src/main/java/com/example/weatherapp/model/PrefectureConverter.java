package com.example.weatherapp.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@code PrefectureConverter} は、都道府県を表す {@link Prefecture} 列挙型と
 * データベースに保存される整数値（都道府県コード）との間の変換を行う JPA コンバータです。
 */
@Converter
public class PrefectureConverter implements AttributeConverter<Prefecture, Integer> {

    /**
     * アプリケーションの {@link Prefecture} オブジェクトをデータベース列に変換します。
     *
     * @param attribute {@link Prefecture} 列挙値
     * @return 都道府県コード（整数）。{@code null} の場合は {@code null} を返します。
     */
    @Override
    public Integer convertToDatabaseColumn(Prefecture attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    /**
     * データベース列の値（都道府県コード）を {@link Prefecture} 列挙値に変換します。
     *
     * @param dbData 都道府県コード（整数）
     * @return 対応する {@link Prefecture} 列挙値。{@code null} の場合は {@code null} を返します。
     */
    @Override
    public Prefecture convertToEntityAttribute(Integer dbData) {
        return dbData != null ? Prefecture.fromCode(dbData) : null;
    }
}
