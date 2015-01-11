FROM dockerfile/java:oracle-java8

ENV DISCOGS_REFRESH_INTERVAL 336

RUN mkdir -p /usr/local/discogs
WORKDIR /usr/local/discogs

CMD ["bash"]