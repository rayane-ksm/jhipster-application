/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import JoueurService from './joueur.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Joueur } from '@/shared/model/joueur.model';

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
  describe('Joueur Service', () => {
    let service: JoueurService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new JoueurService();
      currentDate = new Date();
      elemDefault = new Joueur(123, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', currentDate, 0);
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

      it('should create a Joueur', async () => {
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

      it('should not create a Joueur', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Joueur', async () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            poste: 'BBBBBB',
            numeroMaillot: 1,
            telephone: 'BBBBBB',
            dateNaissance: dayjs(currentDate).format(DATE_TIME_FORMAT),
            prixTransfer: 1,
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

      it('should not update a Joueur', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Joueur', async () => {
        const patchObject = Object.assign(
          {
            poste: 'BBBBBB',
            numeroMaillot: 1,
          },
          new Joueur(),
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

      it('should not partial update a Joueur', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Joueur', async () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            poste: 'BBBBBB',
            numeroMaillot: 1,
            telephone: 'BBBBBB',
            dateNaissance: dayjs(currentDate).format(DATE_TIME_FORMAT),
            prixTransfer: 1,
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

      it('should not return a list of Joueur', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Joueur', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Joueur', async () => {
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
