/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import EntraineurService from './entraineur.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Entraineur } from '@/shared/model/entraineur.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Entraineur Service', () => {
    let service: EntraineurService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new EntraineurService();
      currentDate = new Date();
      elemDefault = new Entraineur(123, 'AAAAAAA', 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateNaissance: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Entraineur', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dateNaissance: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Entraineur', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Entraineur', async () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            numIdent: 1,
            dateNaissance: dayjs(currentDate).format(DATE_TIME_FORMAT),
            ancienneEquipe: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Entraineur', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Entraineur', async () => {
        const patchObject = Object.assign(
          {
            nom: 'BBBBBB',
            dateNaissance: dayjs(currentDate).format(DATE_TIME_FORMAT),
            ancienneEquipe: 'BBBBBB',
          },
          new Entraineur(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Entraineur', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Entraineur', async () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            numIdent: 1,
            dateNaissance: dayjs(currentDate).format(DATE_TIME_FORMAT),
            ancienneEquipe: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Entraineur', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Entraineur', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Entraineur', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
