/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { SkillCandidatoDeleteDialogComponent } from 'app/entities/skill-candidato/skill-candidato-delete-dialog.component';
import { SkillCandidatoService } from 'app/entities/skill-candidato/skill-candidato.service';

describe('Component Tests', () => {
  describe('SkillCandidato Management Delete Component', () => {
    let comp: SkillCandidatoDeleteDialogComponent;
    let fixture: ComponentFixture<SkillCandidatoDeleteDialogComponent>;
    let service: SkillCandidatoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [SkillCandidatoDeleteDialogComponent]
      })
        .overrideTemplate(SkillCandidatoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillCandidatoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillCandidatoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
